module.exports = function(grunt) {
	grunt.initConfig({
		pkg: grunt.file.readJSON('package.json'),

		clean: ['build'],

		copy: {
			main: {
				files: [
					{
						// includes files within path and its sub-directories
						expand: true,
						cwd: 'app',
						src: ['**'],
						dest: 'build/'
					}
				]
			}
		},

		htmlmin: {
			dist: {
				options: {
					removeComments: true,
					collapseWhitespace: true
				},
				files: [{
					expand: true,
					cwd: 'build/views/',
					src: '**/*.html',
					dest: 'build/views/templates/'
				}]
			}
		},

		html2js: {
			options: {
				// custom options, see below
			},
			main: {
				src: ['build/views/templates/**/*.html'],
				dest: 'build/views/templates/templates.js'
			}
		},

		jshint: {
			all: ['Gruntfile.js', 'build/js/*.js', 'build/views/**/*.js'],
			options: {
				ignores: ['build/js/angular/*.js']
			}
		},

		concat: {
			options: {
				separator: ";",
				stripBanners: true,
				banner: '/*<%= pkg.name %> - v<%= pkg.version %> - <%= grunt.template.today("yyyy-mm-dd") %>*/\n'
			},

			basic_and_extras: {
				files: {
					'build/css/<%= pkg.name %>.css': [
						'build/css/main.css',
						'build/css/reset.css',
						'build/views/**/*.css'
					],
					'build/js/<%= pkg.name %>.js': [
						'build/js/app.js',
						'build/js/controllers/**/*.js',
						'build/js/directives/**/*js',
						'build/js/services/**/*.js',
						'build/views/**/*.js'
					]
				}
			}
		},

		cssmin: {
			add_banner: {
				options: {
					banner: '/*<%= pkg.name %> - v<%= pkg.version %> - <%= grunt.template.today("yyyy-mm-dd") %>*/'
				},
				files: {
					'build/css/<%= pkg.name %>.min.css': ['build/css/<%= pkg.name %>.css']
				}
			}
		},

		uglify: {
			options: {
				stripBanners: true,
				banner: '/*<%= pkg.name %> - v<%= pkg.version %> - <%= grunt.template.today("yyyy-mm-dd") %>*/\n'
			},
			my_target: {
				files: [{
					'build/js/<%= pkg.name %>.min.js' : ['build/js/<%= pkg.name %>.js']
				}]
			}
		},

		watch: {
			options: {
				livereload: true
			},
			html: {
				files: ['app/views/**/*.html'],
				tasks: ['htmlmin', 'html2js', 'jshint', 'concat', 'uglify', 'removelogging']
			},
			css: {
				files: ['app/css/main.css', 'app/css/reset.css', 'app/views/**/*.css'],
				tasks: ['concat', 'cssmin']
			},
			scripts: {
				files: ['app/js/app.js', 'app/views/**/*.js'],
				tasks: ['jshint', 'concat', 'uglify', 'removelogging']
			}
		},

		removelogging: {
			dist: {
				src: 'build/js/<%= pkg.name %>.min.js',
				dest: 'build/js/<%= pkg.name %>.clean.js'
			}
		}
	});

	grunt.loadNpmTasks('grunt-contrib-clean');
	grunt.loadNpmTasks('grunt-contrib-concat');
	grunt.loadNpmTasks('grunt-contrib-copy');
	grunt.loadNpmTasks('grunt-contrib-cssmin');
	grunt.loadNpmTasks('grunt-contrib-htmlmin');
	grunt.loadNpmTasks('grunt-contrib-jshint');
	grunt.loadNpmTasks('grunt-contrib-uglify');
	grunt.loadNpmTasks('grunt-contrib-watch');
	grunt.loadNpmTasks('grunt-html2js');
	grunt.loadNpmTasks('grunt-nghtml-uglify');
	grunt.loadNpmTasks('grunt-remove-logging');

	grunt.registerTask('default', ['clean', 'copy', 'htmlmin', 'html2js', 'jshint', 'concat', 'cssmin', 'uglify', 'removelogging']);
	grunt.registerTask('test', []);
};
























