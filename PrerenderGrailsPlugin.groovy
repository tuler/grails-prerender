import com.github.greengerong.PreRenderSEOFilter

class PrerenderGrailsPlugin {
	// the plugin version
	def version = "1.0.0"
	// the version or versions of Grails the plugin is designed for
	def grailsVersion = "2.0 > *"
	// resources that are excluded from plugin packaging
	def pluginExcludes = [
		"grails-app/views/error.gsp"
	]

	def title = "Prerender Plugin" // Headline display name of the plugin
	def author = "Danilo Tuler"
	def authorEmail = "danilo.tuler@ideais.com.br"
	def description = '''\
Installs a servlet filter to proxy requests to prerender web service, to provider better SEO for AJAX powered websites. Learn more at http://prerender.io.
'''

	// URL to the plugin's documentation
	def documentation = "https://github.com/tuler/grails-prerender"

	// Extra (optional) plugin metadata

	// License: one of 'APACHE', 'GPL2', 'GPL3'
	def license = "APACHE"

	// Details of company behind the plugin (if there is one)
	def organization = [ name: "Ideais", url: "http://www.ideais.com.br/" ]

	// Any additional developers beyond the author specified above.
	// def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

	// Location of the plugin's issue tracker.
	// def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

	// Online location of the plugin's browseable source code.
	def scm = [ url: "https://github.com/tuler/grails-prerender" ]

	def doWithWebDescriptor = { xml ->
		def cfg = application.config.prerender
		
		def contextParam = xml.'context-param'
		contextParam[contextParam.size() - 1] + {
			'filter' {
				'filter-name'('prerender')
				'filter-class'(PreRenderSEOFilter.name)
				
				if (cfg.prerenderServiceUrl) {
					'init-param' {
						'param-name'('prerenderServiceUrl')
						'param-value'(cfg.prerenderServiceUrl)
					}
				}
				
				if (cfg.crawlerUserAgents) {
					'init-param' {
						'param-name'('crawlerUserAgents')
						'param-value'(cfg.crawlerUserAgents)
					}
				}
			}
		}
		
		def urlPattern = cfg.url?.pattern ?: '/*'
		List list = urlPattern instanceof List ? urlPattern : [urlPattern]
		
		def filter = xml.'filter'
		list.each { pattern -> 
			filter[0] + {
				'filter-mapping' {
					'filter-name'('prerender')
					'url-pattern'(pattern)
				}
			}
		}
	}

	def getWebXmlFilterOrder() {
		def FilterManager = getClass().getClassLoader().loadClass('grails.plugin.webxml.FilterManager')
		['prerender': FilterManager.DEFAULT_POSITION - 400]
	}
	
	def doWithSpring = {
		// TODO Implement runtime spring config (optional)
	}

	def doWithDynamicMethods = { ctx ->
		// TODO Implement registering dynamic methods to classes (optional)
	}

	def doWithApplicationContext = { applicationContext ->
		// TODO Implement post initialization spring config (optional)
	}

	def onChange = { event ->
		// TODO Implement code that is executed when any artefact that this plugin is
		// watching is modified and reloaded. The event contains: event.source,
		// event.application, event.manager, event.ctx, and event.plugin.
	}

	def onConfigChange = { event ->
		// TODO Implement code that is executed when the project configuration changes.
		// The event is the same as for 'onChange'.
	}

	def onShutdown = { event ->
		// TODO Implement code that is executed when the application shuts down (optional)
	}
}
