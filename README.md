# WebScraper
Writes the number of links to get to a target url from a starting url to a output.txt. It was built as a way to check results from the game where starting from a random wikipedia page, you would try to get to the page on disneyland.

There are two search functions, findDepth, which searches up to maxDepth in a depth first manner, and findDepthBreadth, which does the same using a breadth first search, and is most often faster.

It uses Jsoup to access and scrape the webpages. The last thing too note is that given the extremely high number of links on some webpages (especially wikipedia, guinuea pigs alone has 1,500 links and over 400,000 on each of those) it can take a long time to finish. It can be used to check how quick it is to access a page on a website from its home page as well.