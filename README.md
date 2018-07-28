# What is this?
This was one of my first attempts at building out a non-trivial native Android app.

I noticed that there weren't any apps that created a smooth experience for browsing the Canadian Job Bank job board, and that there were no APIs because much of the data isn't owned by the Job Bank or the government.
## My goal
To create a minimal app that acted as a better interface to the website, perhaps also allowing push notifications and other ancillary features for tracking jobs.

## Constraints
1. No server

I could have more easily developed a web scraper using any number of server side technologies. This would have certain advantages, such as being able to centrally cache results, reduce the memory footprint of the app, and reduce the network usage for the individual. I'd also be able to centrally manage the scraping code in the case where the source web page changes and breaks the code rather than having to push out updates.
The disadvantage was that this would have created a central point of failure and introduce new problems. For example, I'd have to maintain a sizable list of proxies—at a minimum—to avoid standard bot blocking methods.
2. Needed to work on older devices just on principle.
3. I don't remember the rest

## Why I didn't finish
Modelling the source website data, if my app was to be reasonably useful, turned out to be way more complex than I originally thought. This is because the job bank indexes job postings from all over the web, and I didn't have the time or resources to figure out how to normalize the page data.
This complexity also resulted from all sorts of things that I'm sure more experienced web scrapers are used to, such as odd page character encodings, bi-lingual postings, external links to monster that load third-party recruitment sites within monster.ca

It may have been a solvible problem, but the scope became too much for the side project that it was. Good learning experience though.

## Current state
The code is quite a mess, but it does compile. Seems to crash immediately, likely because the urls are out of date and I'm not handling exceptions well. I didn't look too far into it.
