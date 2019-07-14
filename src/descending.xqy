<xhtml:html xmlns:xhtml="http://www.w3.org/1999/xhtml" xml:lang="en">
  <head>
  <link rel="stylesheet" href="style.css" />
    <title>All Document</title>
  </head>
  <xhtml:body>
		<h1>Sort Documents Name</h1>
		<label>AscendingOrder</label>
	 
	{
		for $x in fn:doc()
		order by fn:substring(fn:base-uri($x),38) descending
		return 	
		<p><b></b>&nbsp;($x/substring(fn:base-uri($x),38)}</p>
	}


</xhtml:body>
</xhtml:html>)