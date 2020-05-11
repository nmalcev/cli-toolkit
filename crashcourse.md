### Tmux

List of most useful hot keys:
- `[Cb + %]`: to split on left and right panels
- `[Cb + "]`: to split on top and bottom panels
- `[Cb + top|left|right|bottom]`: goto the panel
where
`Cb = [Ctrl + b]`

### Screen commands

1. To launch screen `#: screen`
2. To disconnect a session [Ctrl + a, d]
3. To get list of sessions `#: screen -ls`
4. To restore a session: `#: screen -r 7849`



### Zip

Compressing folders and files with encryption
`zip -re zip_folder.zip file.txt folder`

Compressing a folder with the exclusion of some files and subfolders
`zip -r "backup.zip" folder todo.txt prj -x "*node_modules/*" "*/.DS_Store"`

Compressing a folder and splitting into 100 Mb volumes
`zip -r -s 100m filename.zip  compress_folder`



### youtube-dl
Command to install using the pip utility (you can also install using the apt-get command): `pip install --upgrade youtube-dl`
Command to download an audio track in mp3 format: `youtube-dl -x --audio-format mp3 --audio-quality 0 -o "%(title)s.%(ext)s" "https://www.youtube.com/watch?v=gfYzrXmFN84"``

Links:
- [Habr](https://habr.com/ru/post/369853/)
- [Linuxtricks](https://www.linuxtricks.fr/wiki/youtube-dl)
- [pdf](https://epn.salledesrancy.com/wp-content/uploads/2018/06/TUTO-youtube.pdf)



### Nano shortcuts

- `^Y`: move to the previous screen
- `^V`: move to the next screen
- `^_ [Alt+G]`: go to the line
- `[Alt+W]`: repeat last search
- `[Ctrl+A]`: move to the beginning of the current line
- `[Ctrl+E]`: move to the end of current line
- `[Alt+3]`: comment lines
 


### How to paste text to the clipboard

Commands:
``` bash
#: sed -n '180p' file.txt | xsel -b
#: sed -n '180,182p' file.txt | xsel -b
```
1. The `sed` utility extracts the string with number 180 (and range of strings from 180 to 182) from the file
2. The `xsel` utility insertes them into the buffer



### Downloading a file from a remote host

`#: scp root@1.2.3.4:/root/pcfilename.rar ./ `

### Downloading all resources from a text file

`#: wget -i <filepath>`


### Local web server

Command to start python web server from local folder:
`python -m SimpleHTTPServer 8000`
	
### Which
`which` is an utility that resolves path to the program:

``` bash
#: which nvcc
/usr/local/cuda-6.5/bin/nvcc
```

### How to find the a fragment in files

```
#: grep ConfigProto ./**/*.py
#: grep -r "name" .
```



## Image processing

### Opening an image viewer from the terminal

GPicView is a default image viewer in Lubuntu
`#: gpicview <file path>`

### Cropping images with ImageMagick

ImageMagic has a graphical interface for image processing:
`#: display <img>`


****

Detecting an application that has occupied a port:
`lsof -i :9000`

Discovery of the process that holds the port:
`sudo netstat -ltnp`


### Package management
Checking if a package has been installed?
`dpkg -l {package_name}`

### Java
To find the default Java path: `readlink -f /usr/bin/java | sed "s:bin/java::"`


### translate-shell

Installation in Ubuntu: `sudo apt install translate-shell`
Example: `trans en:ru "any phrase"`

