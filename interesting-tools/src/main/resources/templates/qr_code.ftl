<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8"/>
    <title></title>
</head>
<body>
<h2>二维码内容生成</h2>
<form action="qrCode" method="post" enctype="multipart/form-data">
    <span>内容：<input type="text" name="content"></span>
    <br/><br/>
    <span>logo：<input type="file" name="logoFile"></span>
    <br/><br/>
    <input type="submit" value="提交">
</form>
</body>
</html>
