<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Profile</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.3/css/bulma.min.css">
</head>
<body>
    <section class="section">
        <div class="container">
            <h1 class="title">Welcome <%= request.getAttribute("username") %>!</h1>
            <div class="content">
                <p>Your favorite sport: <strong>${sport}</strong>.</p>
                <p>Your favorite drink: <strong>${drink}</strong>.</p>
            </div>
            <div class="field is-grouped">
                <div class="control">
                    <form action="${pageContext.request.contextPath}/fetch" method="get">
                        <button class="button is-primary" type="submit">Fetch</button>
                    </form>
                </div>
                <div class="control">
                    <form action="${pageContext.request.contextPath}/logout" method="get">
                        <button class="button is-danger" type="submit">Logout</button>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>
