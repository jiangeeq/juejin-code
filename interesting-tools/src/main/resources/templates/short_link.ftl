<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8"/>
    <title></title>
</head>
<body>
<h2>短链接转换</h2>
<form action="shortLink" method="post">
    要转换的url：<textarea  rows="1" style="width: 432px; height: 43px;" name="url">${url?default('')}</textarea>
    <br/><br/>
    <input type="submit" value="提交"/>
</form>

<#if shortUrl?? && shortUrl != "">
    <a href="${shortUrl}" target="_blank">${shortUrl}</a>
</#if>

</body>
</html>
