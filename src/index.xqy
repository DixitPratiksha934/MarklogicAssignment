xdmp:set-response-content-type("text/html"),
('<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">',
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
  <head>
    <title>Sorting With Users Choice</title>
  </head>
  <body>
   <form method="get" action="./results.xqy">
	<div class='ascen'>
	 <label id="ascending">AscendingOrder</label>
	 <input type="radio" name="order" value="Ascending">
	 </input>
	</div>
	<div class='descen'>
	 <label id="descending">DescendingOrder</label>
	 <input type="radio" name="order" value="Descending">
	 </input>
	</div>
	<div id='sub'>
      <input type="submit" value="Search!"/>
	</div>
    </form>
	   
	{
	for $x in fn:doc()
	return 	
	<b><p>{$x/substring(fn:base-uri($x),38)}</p></b>
	}
 </body>
</html>)
