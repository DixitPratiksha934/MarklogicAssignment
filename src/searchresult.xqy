xquery version "1.0-ml";
import module namespace search = "http://marklogic.com/appservices/search" at "/MarkLogic/appservices/search/search.xqy";
declare namespace ns = "http://www.w3.org/1999/xhtml";
declare function local:result-controller()
{	
		if(xdmp:get-request-field("term"))
		then local:search-results()
		else local:default-results()
};

declare function local:default-results()
{
   (for $doc in fn:doc()	
	return <div class="result-item">
				<span class="article-heading">
					<p>
					{$doc/ns:html/ns:body/ns:div/ns:h1/text()}
					</p>
				</span><br/>
				{$doc/ns:html/ns:body/ns:div/ns:p[1]/string(), " ", $doc/ns:html/ns:body/ns:div/ns:p[2]/string()}
				
			</div>) [1 to 10]
};

declare function local:search-results()
{
	for $result in search:search(xdmp:get-request-field("term"))/search:result
	let $uri := fn:string($result/@uri)
    let $doc := fn:doc($uri)
	return <div class="result-item">
				<span class="article-heading">
					<p>
					{$doc/ns:html/ns:body/ns:div/ns:h1/text()}
					</p>
				</span><br/>
				{
				for $text in $result/search:snippet/search:match/node() 
				return
					if(fn:node-name($text) eq xs:QName("search:highlight"))
					then <span class="highlight">{$text/text()}</span>
					else ($text, " ")
				}				
			</div>
};
xdmp:set-response-content-type("text/html; charset=utf-8"),
<html>
<head>
<title>Search</title>
<link href="search.css" rel="stylesheet" type="text/css"/>
</head>
<body>
	<div class="gridContainer clearfix">
      <div class="header"><br/><h1>Search</h1></div>
      <div class="section">
		<div class="main-column">  
			<div id="form">
				<form name="form" method="get" action="searchresult.xqy" id="form">
					<input type="text" name="term" id="term" size="40" value="{xdmp:get-request-field("term")}"/>
					<input type="submit" name="submitbtn" id="submitbtn" value="search"/>
				</form> 
				<br/>
				{local:result-controller()}				
			</div>
		</div>
	  </div>
      
	</div>
</body>
</html>