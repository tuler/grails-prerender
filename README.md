prerender Plugin
================

Nowadays it's fairly common to implement websites using lots of AJAX and javascript generated contents. This improves usability but it's bad for search engines, because javascript is not processed by them.

Google came up with a proposal to enhance the SEO quality of such websites in a  "blessed" way: [https://developers.google.com/webmasters/ajax-crawling/docs/getting-started](https://developers.google.com/webmasters/ajax-crawling/docs/getting-started).

One of the steps of the process is to generate HTML snapshots of your webpages. There comes [prerender.io](http://prerender.io) to the rescue. This is a server written in [node.js](http://nodejs.org) which uses [phantomjs](http://phantomjs.org) to render pages on demand.

All you need to do is proxy requests coming from crawlers to a prerender server. This grails plugin add a servlet filter which makes this proxy.

Actually this is just a thin wrapper around the filter provided by [https://github.com/greengerong/prerender-java](https://github.com/greengerong/prerender-java).

Using
-----

Add a dependency to BuildConfig.groovy:

	plugins {
		runtime ":prerender:1.0.1"
		...
	}

The default configuration installs a servlet filter that proxies the request to http://prerender.herokuapp.com according to the rules defined by [google](https://developers.google.com/webmasters/ajax-crawling/docs/getting-started).

Basically it checks if the User-Agent is a crawler or if there is a query parameter called "\_escaped\_fragment\_".

Configuration
-------------

The plugin is configured through Config.groovy.

You can change the prerender service url using the following configuration:

	prerender.prerenderServiceUrl = 'http://localhost:3000'

The default value is 'http://prerender.herokuapp.com'

You can also change the user agents the filter applies to, separated by comma. This will be added to the default ones. Example: 

	prerender.crawlerUserAgents = 'Ezooms,MSNBot,Exabot'

Changelog
---------

1.0.1: Upgraded prerender-java dependency to 1.0.1. Fix for User-Agent detection code.
1.0.0: First working version

