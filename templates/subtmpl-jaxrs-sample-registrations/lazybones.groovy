import org.apache.commons.io.FileUtils
import org.apache.commons.io.FilenameUtils

import static org.apache.commons.io.FilenameUtils.concat

def params = [:]
params.pkg = "${parentParams.packageName}"

// Pass in parameters from the project template
params.parentGroup = parentParams.group
params.parentVersion = parentParams.version

processTemplates("main/**/*", params)
processTemplates("test/**/*", params)

def pkgPath = params.pkg.replace('.' as char, '/' as char)

new File(projectDir, concat("src/main/java", pkgPath) ).mkdirs()
new File(projectDir, concat("src/main/resources", pkgPath) ).mkdirs()
new File(projectDir, concat("src/test/java", pkgPath) ).mkdirs()

def mainJava = concat('main', 'java')
FileUtils.moveDirectoryToDirectory(new File(templateDir, concat('main', 'java/registrations')), new File(concat("src/main/java", pkgPath)), true )
FileUtils.moveDirectoryToDirectory(new File(templateDir, concat('main', 'java/tracing')), new File(concat("src/main/java", pkgPath)), true )
FileUtils.moveDirectoryToDirectory(new File(templateDir, concat('main', 'resources/META-INF')), new File("src/main/resources"), true )
FileUtils.moveDirectoryToDirectory(new File(templateDir, concat('main', 'webapp')), new File("src/main"), true )

FileUtils.moveDirectoryToDirectory(new File(templateDir, concat('test', 'java/registrations')), new File("src/test/java/$pkgPath"), true )
FileUtils.moveDirectoryToDirectory(new File(templateDir, concat('test', 'java/tracing')), new File("src/test/java/$pkgPath"), true )
FileUtils.moveDirectoryToDirectory(new File(templateDir, concat('test', 'stress')), new File("src/test"), true )

