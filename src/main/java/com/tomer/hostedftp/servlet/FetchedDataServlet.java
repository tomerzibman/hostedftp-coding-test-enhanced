package com.tomer.hostedftp.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tomer.hostedftp.util.HttpJsonRequestHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/fetch")
public class FetchedDataServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(FetchedDataServlet.class.getName());
	private final String catEndpoint = "https://catfact.ninja/fact";
	private final String dogEndpoint = "https://dog.ceo/api/breeds/image/random";
	private final String bitCoinEndpoint = "https://api.coindesk.com/v1/bpi/currentprice.json";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("authenticated") == null || !(boolean) session.getAttribute("authenticated")) {
			response.sendRedirect(request.getContextPath() + "/login");
			return;
		}
		
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		
		CompletableFuture<Map<String, String>> catFutureAttributes = CompletableFuture.supplyAsync(() -> fetchCatData(), executorService);
		CompletableFuture<Map<String, String>> dogFutureAttributes = CompletableFuture.supplyAsync(() -> fetchDogData(), executorService);
		CompletableFuture<Map<String, String>> bitCoinFutureAttributes = CompletableFuture.supplyAsync(() -> fetchBitCoinData(), executorService);
		
		CompletableFuture<Void> allFutures = CompletableFuture.allOf(catFutureAttributes, dogFutureAttributes, bitCoinFutureAttributes);
		
		try {
			allFutures.join();
			
			Map<String, String> allAttributes = new HashMap<>();
			allAttributes.putAll(catFutureAttributes.join());
			allAttributes.putAll(dogFutureAttributes.join());
			allAttributes.putAll(bitCoinFutureAttributes.join());
			
			for (Map.Entry<String, String> kvp : allAttributes.entrySet()) {
				request.setAttribute(kvp.getKey(), kvp.getValue());
			}
			request.getRequestDispatcher("/jsp/fetched.jsp").forward(request, response);
		} catch (Exception exception) {
			logger.severe("An exception occured: " + exception.getMessage());
			exception.printStackTrace();
		} finally {
			executorService.shutdown();
		}
	}
	
	private Map<String, String> fetchCatData() {
		Map<String, String> attributes = new HashMap<>();
		
		String catJson = null;
		try {
			catJson = HttpJsonRequestHandler.getJson(catEndpoint);
		} catch (Exception exception) {
			logger.severe("An IO Exception occured: " + exception.getMessage());
			exception.printStackTrace();
			return attributes;
		}
		
		Pattern factPattern = Pattern.compile("\"fact\"\\s*:\\s*\"([^\"]*)\"");
        Pattern lengthPattern = Pattern.compile("\"length\"\\s*:\\s*(\\d+)");

        Matcher factMatcher = factPattern.matcher(catJson);
        Matcher lengthMatcher = lengthPattern.matcher(catJson);
        
        String fact = null;
        String length = null;
        
        if (factMatcher.find() && lengthMatcher.find()) {
        	fact = factMatcher.group(1);
        	length = lengthMatcher.group(1);
        }
        
        attributes.put("fact", fact);
        attributes.put("length", length);
        return attributes;
	}
	
	private Map<String, String> fetchDogData() {
		Map<String, String> attributes = new HashMap<>();
		
		String dogJson = null;
		try {
			dogJson = HttpJsonRequestHandler.getJson(dogEndpoint);
		} catch (Exception exception) {
			logger.severe("An IO Exception occured: " + exception.getMessage());
			exception.printStackTrace();
			return attributes;
		}
        
        Pattern messagePattern = Pattern.compile("\"message\"\\s*:\\s*\"([^\"]*)\"");
        Pattern statusPattern = Pattern.compile("\"status\"\\s*:\\s*\"([^\"]*)\"");
        
        Matcher messageMatcher = messagePattern.matcher(dogJson);
        Matcher statusMatcher = statusPattern.matcher(dogJson);
        
        String message = null;
        String status = null;
        
        if (messageMatcher.find() && statusMatcher.find()) {
        	message = messageMatcher.group(1);
        	status = statusMatcher.group(1);
        }
        
        attributes.put("message", message);
        attributes.put("status", status);
        return attributes;
	}
	
	private Map<String, String> fetchBitCoinData() {
		Map<String, String> attributes = new HashMap<>();
		
		String bitCoinJson = null;
		try {
			bitCoinJson = HttpJsonRequestHandler.getJson(bitCoinEndpoint);
		} catch (Exception exception) {
			logger.severe("An IO Exception occured: " + exception.getMessage());
			exception.printStackTrace();
			return attributes;
		}
		
		Pattern pattern = Pattern.compile("\"time\":\\{.*?\"updated\":\"(.*?)\".*?\\},\"disclaimer\":\"(.*?)\",\"chartName\":\"(.*?)\",\"bpi\":\\{(.*?)\\}\\}");
        Matcher matcher = pattern.matcher(bitCoinJson);
        String[] currencyCodes = { "usd", "gbp", "eur" };

        if (matcher.find()) {
            String updatedTime = matcher.group(1);
            String disclaimer = matcher.group(2);
            String chartName = matcher.group(3);
            String bpiData = matcher.group(4);
            
            attributes.put("updatedTime", updatedTime);
            attributes.put("disclaimer", disclaimer);
            attributes.put("chartName", chartName);

            Pattern currencyPattern = Pattern.compile("\"(\\w+)\":\\{.*?\"rate\":\"([\\d,\\.]+)\"");
            Matcher currencyMatcher = currencyPattern.matcher(bpiData);
            
            int index = 0;
            while (currencyMatcher.find()) {
                String rate = currencyMatcher.group(2).replace(",", "");
                
                attributes.put(currencyCodes[index] + "Rate", rate);
                index++;
            }
        }
        return attributes;
	}

}
