<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%

    MemberRepository memberRepository = MemberRepository.getInstnace();
      List<Member> members = memberRepository.findAll();


%>
<html>
<head>
 <title>Title</title>
</head>
<body>
<a href="/index.html">메인</a>
<table>
    <thead>
    <th>id</th>
    <th>username</th>
    <th>age</th>
    </thead>
    <tbody>
   <%
   for (Member member : members) {
               w.write("<tr>");
               w.write("<th>"+member.getId()+"</th>");
               w.write("<th>"+member.getUsername()+"</th>");
               w.write("<th>"+member.getAge()+"</th>");
               w.write("</tr>");
           }

   %>

    </tbody>

</table>

</form>
</body>
</html>