# FtpLoader
TestApp spring, files, ftp, base log

use BasicAuth name:"user", pass:"password"

http://localhost:8080/file/check?path=path - check files in directory
http://localhost:8080/file/upload?path=path - upload this files(only, not a directory) to virtual db (h2)
http://localhost:8080/file/view - view result of upload files
http://localhost:8080/visit/ - check your motions
