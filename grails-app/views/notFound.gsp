<!doctype html>
<html>
    <head>
        <title>Page Not Found</title>
        <meta name="layout" content="none">
        <g:if env="development"><asset:stylesheet src="errors.css"/></g:if>
    </head>
    <body>
        <ul class="errors">
           <h6><li>Error: Page Not Found (404)</li></h6>
            <li>Path: ${request.forwardURI}</li>
        </ul>
    </body>
</html>
