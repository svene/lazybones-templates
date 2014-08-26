import org.apache.commons.io.FileUtils
import org.apache.commons.io.FilenameUtils

import static org.apache.commons.io.FilenameUtils.concat

def params = [:]
params.pkg = "${parentParams.packageName}"

// Pass in parameters from the project template
params.parentGroup = parentParams.group
params.parentVersion = parentParams.version

processTemplates("content/**/*", params)

def pkgPath = params.pkg.replace('.' as char, '/' as char)

copyContent('content/src/main/java', 'src/main/java', pkgPath)
copyContent('content/src/main/resources', 'src/main/resources')
copyContent('content/src/test/java', 'src/test/java', pkgPath)
copyContent('content/src/test/stress', 'src/test/stress')

def copyContent(String sourceFolder, String targetFolder, String pkgPath=null) {
	File targetDir = new File(targetFolder)
	File targetPath = pkgPath ? new File(targetDir, pkgPath) : targetDir
	targetPath.mkdirs()

	File sourcesDir = new File(templateDir, sourceFolder)
	sourcesDir.eachFile { File file ->
	   file.renameTo("${targetPath.absolutePath}/${file.name}")
	}
}
