<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Fetched Data</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.3/css/bulma.min.css">
</head>
<body>
    <section class="section">
        <div class="container">
            <h1 class="title">Fetched Data</h1>
            <table class="table is-bordered is-striped is-narrow is-hoverable">
                <thead>
                    <tr>
                        <th colspan="2">Cat Facts</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Fact</td>
                        <td>${fact}</td>
                    </tr>
                    <tr>
                        <td>Length</td>
                        <td>${length}</td>
                    </tr>
                </tbody>
                <thead>
                    <tr>
                        <th colspan="2">Dog Image</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Status</td>
                        <td>${status}</td>
                    </tr>
                    <tr>
                        <td>Image</td>
                        <td><img src="${message}" alt="Image"></td>
                    </tr>
                </tbody>
                <thead>
                    <tr>
                        <th colspan="2">Bitcoin Data</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>Updated Time</td>
                        <td>${updatedTime}</td>
                    </tr>
                    <tr>
                        <td>Disclaimer</td>
                        <td>${disclaimer}</td>
                    </tr>
                    <tr>
                        <td>Chart Name</td>
                        <td>${chartName}</td>
                    </tr>
                    <tr>
                        <td>USD Rate</td>
                        <td>${usdRate}</td>
                    </tr>
                    <tr>
                        <td>GBP Rate</td>
                        <td>${gbpRate}</td>
                    </tr>
                    <tr>
                        <td>EUR Rate</td>
                        <td>${eurRate}</td>
                    </tr>
                </tbody>
            </table>
            <div class="buttons">
                <a class="button is-primary" href="${pageContext.request.contextPath}/user">Back to User Profile</a>
                <a class="button is-danger" href="${pageContext.request.contextPath}/logout">Logout</a>
            </div>
        </div>
    </section>
</body>
</html>
