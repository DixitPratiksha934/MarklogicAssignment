xdmp:set-response-content-type("text/html"),
('<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">',
<xhtml:html xmlns:xhtml="http://www.w3.org/1999/xhtml" xml:lang="en">
  <head>
   <link rel="stylesheet" href="style.css" />
    <title>Sorting Results</title>
  </head>
  <xhtml:body>
  <h1>Documents Order : {xdmp:get-request-field("order")}</h1>
    {
    
	if(xdmp:get-request-field("order") = "Ascending") then (
          for $x in fn:doc()
		order by fn:substring(fn:base-uri($x),38) ascending
		return 	
		<b><p>{$x/substring(fn:base-uri($x),38)}</p></b>
   )
   else if(xdmp:get-request-field("order") = "Descending") then
   ( 
      for $x in fn:doc()
		order by fn:substring(fn:base-uri($x),38) descending
		return 	
		<b><p>{$x/substring(fn:base-uri($x),38)}</p></b>
   ) 
   else(	
	<b><p>No Order Is Selected</p></b>
	)
  }

  </xhtml:body>
</xhtml:html>)
