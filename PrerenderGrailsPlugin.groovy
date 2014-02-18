import com.github.greengerong.PreRenderSEOFilter

class PrerenderGrailsPlugin {
	def version = "1.0.6"
	def grailsVersion = "2.0 > *"
	def title = "Prerender Plugin"
	def author = "Danilo Tuler"
	def authorEmail = "danilo.tuler@ideais.com.br"
	def description = '''\
Installs a servlet filter to proxy requests to prerender web service, to provider better SEO for AJAX powered websites. Learn more at http://prerender.io.
'''

	def documentation = "https://github.com/tuler/grails-prerender"
	def license = "APACHE"
	def organization = [ name: "Ideais", url: "http://www.ideais.com.br/" ]
	def issueManagement = [ system: "GITHUB", url: "https://github.com/tuler/grails-prerender/issues" ]
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
				
				if (cfg.extensionsToIgnore) {
					'init-param' {
						'param-name'('extensionsToIgnore')
						'param-value'(cfg.extensionsToIgnore)
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
		[prerender: FilterManager.DEFAULT_POSITION - 400]
	}
}
