<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Index</title>
</head>
<body>
${name}


<input type="text" name="message" />
<input id="sendBtn" type="button" value="send" />

<div id="messageBoard"></div>
<script type="text/javascript" src="/js/jquery-2.1.4.min.js"></script>
<script type="text/javascript">
    $(document).on('click', '#sendBtn', function(){
        var str = $('#messageBoard').html() + "<br / >" + 'msg from java : ' + $('[name="message"]').val();
        $('#messageBoard').html(str);
        $.post('/message', {'message' : $('[name="message"]').val()}, function(ret){
            var str = $('#messageBoard').html() + "<br / >" + ret;
            $('#messageBoard').html(str);
        });
    });
</script>
</body>
</html>