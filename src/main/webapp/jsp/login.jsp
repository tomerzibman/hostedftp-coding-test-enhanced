<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login Form</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.3/css/bulma.min.css">
</head>
<body>
<section class="section">
  <div class="container">
    <h1 class="title">Login</h1>
    <form action="${pageContext.request.contextPath}/login" method="post">
      <div class="field">
        <label class="label">Username</label>
        <div class="control">
          <input class="input" type="text" name="username" placeholder="Enter your username">
        </div>
      </div>
      <div class="field">
        <label class="label">Password</label>
        <div class="control">
          <input class="input" type="password" name="password" placeholder="Enter your password">
        </div>
      </div>
      <div class="field is-grouped">
        <div class="control">
          <button class="button is-primary" type="submit">Login</button>
        </div>
        <div class="control">
          <a href="/hostedftp-coding-test/" class="button is-primary">Back to Index</a>
        </div>
      </div>
    </form>
  </div>
</section>
</body>
</html>
