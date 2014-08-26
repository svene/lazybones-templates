import uk.co.cacoethes.util.NameType

Map props = [:]
File projectDir = projectDir instanceof File ? projectDir : new File(String.valueOf(projectDir))
String projectDirName = ('.' == projectDir.name) ? new File(projectDir.canonicalPath).name : projectDir.name

props.ARG_PROJECT_DIR_NAME = projectDir.name
props.PROJECT_DIR_NAME = projectDirName
props.PROJECT_NAME = transformText(projectDirName, from: NameType.HYPHENATED, to: NameType.PROPERTY)

props.PROJECT_GROUP = ask2('group', 'org.group')
props.PROJECT_VERSION = ask2('version', '0.1.0-SNAPSHOT')
props.project_package_name = ask2('packageName', props.PROJECT_GROUP)

// use '**/...' syntax because with current lazybones version (0.7.1) the antpath match fails without it:
processTemplates '**/build.gradle', props
processTemplates '**/gradle.properties', props
processTemplates '**/settings.gradle', props
processTemplates '**/pom.xml', props

String packagePath = props.project_package_name.replace('.' as char, '/' as char)

File sources = new File(projectDir, 'src/main/java')
File resources = new File(projectDir, 'src/main/resources')

File sourcesPath = new File(sources, packagePath)
sourcesPath.mkdirs()
File resourcesPath = new File(resources, packagePath)
resourcesPath.mkdirs()

sources.eachFile { File file ->
   file.renameTo(sourcesPath.absolutePath + '/' + file.name)
}


def ask2(key, proposal) {
	ask("Define value for '$key' [$proposal]: ", proposal, key)
}