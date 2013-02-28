package com.janrain.bp

trait BackplaneConfig {
	def admin: String
	def secret: String
	def host: String
	def port: Int
}

trait LocalhostBackplaneConfig extends BackplaneConfig {
	val admin = "bpadmin"
	val secret = "bpadmin"
	val host = "localhost"
	val port = 9000
}