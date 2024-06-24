<%--
  Created by IntelliJ IDEA.
  User: USER
  Date: 2024-06-06
  Time: 오전 10:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
    <title>AJAX</title>
</head>
<body>
<form id="user">
    <fieldset>
        <label>로그인 아이디 : </label><input id="loginid" name="id" type="text"/>
        <input id="logincheck" type="button" value="아이디 중복 검사"/><br/>
        <label>비밀번호 : </label><input id="password" name="password" type="password"/><br/>
        <label>이름 : </label><input id="name" name="name" type="text"/><br/>
        <input type="submit" value="등록"/>
    </fieldset>
</form>
<script>
    document.querySelector('#loginCheck').addEventListener("click", () => {
        const url = 'logincheckid/' + document.querySelector('#loginid').value;
        fetch(url, {
            method: "get",
            headers: {'Content-Type': 'application/json'}
        })
            .then(data => data.json())
            .then(data => {
                if (data.duplicated) {
                    alert('이미 등록된 로그인ID입니다. ' + data.availableId + '는 사용할 수 있습니다.')
                }
                else {alert('사용할 수 있는 로그인ID입니다.')}
            })
    });
    document.querySelector('#user').addEventListener("submit", (e) => {
        e.preventDefault();

        // 폼의 모든 입력 필드를 JSON 포맷의 메시지로 만든다.
        const formData = new FormData(e.target);
        const jsonData = JSON.stringify(Object.fromEntries(formData.entries()));

        // POST메소드와 application/json 콘텐트 타입, JSON 메시지 본문을 갖는 요청을 보낸다.
        fetch("user/register", {
            method:"POST",
            headers: {'Content-Type': 'application/json'},
            body: jsonData
        }).then(data => data.json())
            .then(data => console.log(data));
    });
</script>
</body>
</html>
