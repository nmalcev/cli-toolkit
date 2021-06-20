



### FS.Partitions

	Mount folder/filesystem through SSH
	$ sshfs name@server:/path/to/folder /path/to/mount/point

	Mount a temporary ram partition
	$ mount -t tmpfs tmpfs /mnt -o size=1024m
	
### FS.swap

	Create an emergency swapfile when the existing swap space is getting tight
	$ sudo dd if=/dev/zero of=/swapfile bs=1024 count=1024000; sudo mkswap /swapfile; sudo swapon /swapfile
	
### FS.checking

	Check disk for bad sectors
	$ badblocks -n -s -b 2048 /dev/sdX

## Terminal

	Clear the terminal screen
	$ ctrl-l

	Close shell keeping all subprocess running
	$ disown -a && exit

	Kills a process that is locking a file.
	$ fuser -k filename

	
### Terminal.directories

	Mkdir & cd into it as single command
	$ mkdir /home/foo/doc/bar && cd $_

	Make directory including intermediate directories
	$ mkdir -p a/long/directory/path

	Make directory tree
	$ mkdir -p work/{d1,d2}/{src,bin,bak}
	
	Relocate a file or directory, but keep it accessible on the old location throug a simlink.
	$ mv $1 $2 && ln -s $2/$(basename $1) $(dirname $1)


### Terminal.dates

	Convert seconds to human-readable format
	$ date -d@1234567890
	
### Terminal.permissions

	Nicely display permissions in octal format with filename
	$ stat -c '%A %a %n' *
	
	Show permissions of current directory and all directories upwards to /
	$ namei -m $(pwd)

	
### Terminal.clipboard
	
	Pipe output of a command to your clipboard
	$ some command | xsel --clipboard

	Copy stdin to your X11 buffer
	$ ssh user@host cat /path/to/some/file | xclip



## Internet

### Googling

	Search google.com on your terminal
	$ Q="YOURSEARCH"; GOOG_URL="http://www.google.com/search?q="; AGENT="Mozilla/4.0"; stream=$(curl -A "$AGENT" -skLm 10 "${GOOG_URL}\"${Q/\ /+}\"" | grep -oP '\/url\?q=.+?&amp' | sed 's/\/url?q=//;s/&amp//'); echo -e "${stream//\%/\x}"


### Internet.Downloading

	Download an entire website
	$ wget --random-wait -r -p -e robots=off -U mozilla http://www.example.com
	
	Extract tarball from internet without local saving
	$ wget -qO - "http://www. tarball.com/ tarball.gz" | tar zxvf -
	
	Download an entire static website to your local machine
	$ wget --recursive --page-requisites --convert-links www.moyagraphix.co.za

	Download all mp3's listed in an html page
	$ wget -r -l1 -H -t1 -nd -N -np -A.mp3 -erobots=off [url of website]
	
	Use wget to download one page and all it's requisites for offline viewing
	$ wget -e robots=off -E -H -k -K -p http://<page>
	
	Convert a web page into a png
	$ touch $2;firefox -print $1 -printmode PNG -printfile $2


### Internet.file sharing

	Sharing file through http 80 port
	$ nc -v -l 80 < file.ext
	
	Create a file server, listening in port 7000
	$ while true; do nc -l 7000 | tar -xvf -; done
	
	Send data securly over the net.
	$ cat /etc/passwd | openssl aes-256-cbc -a -e -pass pass:password | net cat -l -p 8080

### Internet.SSH

	Copy your SSH public key on a remote machine for passwordless login - the easy way
	$ ssh-copy-id username@hostname

	Copy your ssh public key to a server from a machine that doesn't have ssh-copy-id
	$ cat ~/. ssh/id_rsa.pub | ssh user@machine "mkdir ~/. ssh; cat >> ~/. ssh/authorized_keys"

	Edit a file on a remote host using vim
	$ vim scp://username@host//path/to/somefile
	
	Create a local compressed tarball from remote host directory
	$ ssh user@host "tar -zcf - /path/to/dir" > dir.tar.gz


### Internet.Certificates
	
	Check site ssl certificate dates
	$ echo | openssl s_client -connect www.google.com:443 2>/dev/null |openssl x509 -dates -noout


### Internet.email

	Python smtp server
	$ python -m smtpd -n -c DebuggingServer localhost:1025

	Send email with curl and gmail
	$ curl -n --ssl-reqd --mail-from "<user@gmail.com>" --mail-rcpt "<user@server.tld>" --url smtps://smtp.gmail.com:465 -T file.txt

	```
	<html>
		<body>
			<div>
				<p>Hello, </p>
				<p>Please see the log file attached</p>
				<p>Admin Team</p>
				<img src="cid:admin.png" width="150" height="50">
			</div>
		</body>
	</html>

	```
	
	``` bash
	#!/bin/bash

	rtmp_url="smtp://smtp.gmail.com:587"
	rtmp_from="yandexzombie@gmail.com"
	rtmp_to="nmaltsev@argans.eu"
	rtmp_credentials="yandexzombie@gmail.com:$1"

	zip_upload="/mount/blacksea/To_send/Results_Drift_fd1922fd389578528f3f3ce5d28ff8bd.zip"
	file_upload="data.txt"

	# html message to send
	echo "<html>
	<body>
		<div>
			<p>Hello, </p>
			<p>Please see the log file attached</p>
			<p>Admin Team</p>
			<img src=\"cid:admin.png\" width=\"150\" height=\"50\">
		</div>
	</body>
	</html>" > message.html

	# log.txt file to attached to the mail
	echo "some log in a txt file to attach to the mail" > log.txt

	mail_from="Some Name <$rtmp_from>"
	mail_to="Some Name <$rtmp_to>"
	mail_subject="example of mail"
	mail_reply_to="Some Name <$rtmp_from>"
	mail_cc=""

	# add an image to data.txt :
	# $1 : type (ex : image/png)
	# $2 : image content id filename (match the cid:filename.png in html document)
	# $3 : image content base64 encoded
	# $4 : filename for the attached file if content id filename empty
	function add_file {
		echo "--MULTIPART-MIXED-BOUNDARY
	Content-Type: $1
	Content-Transfer-Encoding: base64" >> "$file_upload"

		if [ ! -z "$2" ]; then
			echo "Content-Disposition: inline
	Content-Id: <$2>" >> "$file_upload"
		else
			echo "Content-Disposition: attachment; filename=$4" >> "$file_upload"
		fi
		echo "$3

	" >> "$file_upload"
	}

	message_base64=$(cat message.html | base64)

	echo "From: $mail_from
	To: $mail_to
	Subject: $mail_subject
	Reply-To: $mail_reply_to
	Cc: $mail_cc
	MIME-Version: 1.0
	Content-Type: multipart/mixed; boundary=\"MULTIPART-MIXED-BOUNDARY\"

	--MULTIPART-MIXED-BOUNDARY
	Content-Type: multipart/alternative; boundary=\"MULTIPART-ALTERNATIVE-BOUNDARY\"

	--MULTIPART-ALTERNATIVE-BOUNDARY
	Content-Type: text/html; charset=utf-8
	Content-Transfer-Encoding: base64
	Content-Disposition: inline

	$message_base64
	--MULTIPART-ALTERNATIVE-BOUNDARY--" > "$file_upload"

	# add an image with corresponding content-id (here admin.png)
	image_base64=$(curl -s "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_116x41dp.png" | base64)

	## the folowing attachment will be inserted into the email body "cid:admin.png"
	add_file "image/png" "admin.png" "$image_base64"

	# add the log file
	log_file=$(cat log.txt | base64)
	add_file "text/plain" "" "$log_file" "log.txt"

	## attach zip archive
	mime_type=$(file --mime-type -b $zip_upload)
	zip_file=$(cat $zip_upload | base64)
	add_file "$mime_type" "" "$zip_file" "result.zip"


	# add another image
	#image_base64=$(curl -s "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_116x41dp.png" | base64)
	#add_file "image/png" "something.png" "$image_base64"

	# end of uploaded file
	echo "--MULTIPART-MIXED-BOUNDARY--" >> "$file_upload"

	# send email
	echo "sending ...."
	curl -s "$rtmp_url" \
		 --mail-from "$rtmp_from" \
		 --mail-rcpt "$rtmp_to" \
		 --ssl -u "$rtmp_credentials" \
		 -T "$file_upload" -k --anyauth
	res=$?
	if test "$res" != "0"; then
	   echo "sending failed with: $res"
	else
		echo "OK"
	fi

	```

	Send email with one or more binary attachments
	$ echo "Body goes here" | mutt -s "A subject" -a /path/to/file.tar.gz recipient@example.com


## System 

### System.information

	32 bits or 64 bits?
	$ getconf LONG_BIT
	
	Display which distro is installed
	$ cat /etc/issue
	
	Display which distro is installed
	$ cat /etc/*release

#### System.information.ip

	What is my public IP-address?
	$ curl ifconfig.me
	
	Find geographical location of an ip address
	$ lynx -dump http://www.ip-adress.com/ip_tracer/?QRY=$1|grep address|egrep 'city|state|country'|awk '{print $3,$4,$5,$6,$7,$8}'|sed 's\ip address flag \\'|sed 's\My\\'

	Get your external IP address
	$ curl ip.appspot.com
	
### System.benchmarks
	
	Memory Benchmark:
	$ dd if=/dev/zero of=/dev/null bs=1M count=32768
	34359738368 bytes (34 GB, 32 GiB) copied, 1,52976 s, 22,5 GB/s

	
### System.activity

	Watch Network Service Activity in Real-time
	$ lsof -i
	
	$ ps aux
	
	Intercept stdout/stderr of another process
	$ strace -ff -e trace=write -e write=1,2 -p SOME_PID

	Which program is this port belongs to ?
	$ lsof -i tcp:80

	Lists all listening ports together with the PID of the associated process
	$ lsof -Pan -i tcp -i udp
	
	Show which process is blocking umount (Device or resource is busy)
	$ lsof /folder


### System.passwords

	Generate a random password 30 characters long
	$ strings /dev/urandom | grep -o '[[:alnum:]]' | head -n 30 | tr -d '\n'; echo


	Create strong, but easy to remember password
	$ read -s pass; echo $pass | md5sum | base64 | cut -c -16



## Documents

	Check your spelling
	$ aspell -a <<< '<WORDS>'

### Documents.diff checking

	Diff two unsorted files without creating temporary files
	$ diff <(sort file1) <(sort file2)
	
	Compare two directory trees.
	$ diff <(cd dir1 && find | sort) <(cd dir2 && find | sort)
	
	Recursively compare two directories and output their differences on a readable format
	$ diff -urp /originaldirectory /modifieddirectory
	
	Compare directories via diff
	$ diff -rq dirA dirB

### Documents.searching

	Search recursively to find a word or phrase in certain file types, such as C code
	$ find . -name "*.[ch]" -exec grep -i -H "search pharse" {} \;

	Find files that have been modified on your system in the past 60 minutes
	$ find / -mmin 60 -type f
	
	Recursive search and replace old with new string, inside files
	$ grep -rl oldstring . | xargs sed -i -e 's/oldstring/newstring/'
	
	Find files containing text
	$ grep -lir "some text" *
	
	Find all the links to a file
	$ find -L / -samefile /path/to/file -exec ls -ld {} +



### Documents.PDF

	Convert PDF to JPG
	$ for file in `ls *.pdf`; do convert -verbose -colorspace RGB -resize 800 -interlace none -density 300 -quality 80 $ file `echo $ file | sed 's/\.pdf$/\.jpg/'`; done

	GREP a PDF file.
	$ pdftotext [file] - | grep 'YourPattern'

	Stamp a text line on top of the pdf pages.
	$ echo "This text gets stamped on the top of the pdf pages." | enscript -B -f Courier-Bold16 -o- | ps2pdf - | pdftk input.pdf stamp - output output.pdf

	Create a single PDF from multiple images with ImageMagick
	$ convert *.jpg output.pdf
	
	Save an HTML page, and covert it to a .pdf file
	$ wget $URL | htmldoc --webpage -f "$URL".pdf - ; xpdf "$URL".pdf &
	
	Merge *.pdf files
	$ gs -q -sPAPERSIZE=letter -dNOPAUSE -dBATCH -sDEVICE=pdfwrite -sOutputFile=out.pdf `ls *.pdf`
	
	Optimize PDF documents
	$ gs -sDEVICE=pdfwrite -dCompatibilityLevel=1.4 -dPDFSETTINGS=/screen -dNOPAUSE -dQUIET -dBATCH -sOutputFile=output.pdf input.pdf
	
### Documents.printing
	??? try on printer
	Sends a postscript file to a postscript printer using netcat
	$ cat my.ps | nc -q 1 hp4550.mynet.xx 9100


	
### Documents.archives

	Download and unpack tarball without leaving it sitting on your hard drive
	$ wget -qO - http://example.com/path/to/blah.tar.gz | tar xzf -

	Split a tarball into multiple parts
	$ tar cf - <dir>|split -b<max_size>M - <name>. tar.

	Extract tar content without leading parent directory
	$ tar -xaf archive. tar.gz --strip-components=1
	
	Create a Multi-Part Archive Without Proprietary Junkware
	$ tar czv Pictures | split -d -a 3 -b 16M - pics. tar.gz.
	
	Split File in parts
	$ split -b 19m file Nameforpart

## Media

	Save command output to image
	$ ifconfig | convert label:@- ip.png


### Media.screencasting

	Record a screencast and convert it to an mpeg
	$ ffmpeg -f x11grab -r 25 -s 800x600 -i :0.0 /tmp/outputFile.mpg
	
### Media.webcam
	Press enter and take a WebCam picture.
	$ read && ffmpeg -y -r 1 -t 3 -f video4linux2 -vframes 1 -s sxga -i /dev/video0 ~/webcam-$(date +%m_%d_%Y_%H_%M).jpeg


### Media.images
	
	Convert text to picture
	$ echo -e "Some Text Line1 Some Text Line 2" | convert -background none -density 196 -resample 72 -unsharp 0x.5 -font "Courier" text:- -trim +repage -bordercolor white -border 3 text.gif

	Remote screenshot
	$ DISPLAY=":0.0" import -window root screenshot.png
	
	Resize an image to at least a specific resolution
	$ convert -resize '1024x600^' image.jpg small-image.jpg
	
	Make image semi-transparent
	$ convert input.png -alpha set -channel A -fx 0.5 output.png
	
	Quick screenshot
	$ import -pause 5 -window root desktop_screenshot.jpg
	
	Show webcam output
	$ mplayer tv:// -tv driver=v4l:width=352:height=288

### Media.Youtube-dl

	Convert Youtube videos to MP3
	$ youtube-dl -t --extract-audio --audio-format mp3 https://www.youtube.com/watch?v=SGnvlW89-Us
	
	Create an animated gif from a Youtube video
	$ url=http://www.youtube.com/watch?v=V5bYDhZBFLA; youtube-dl -b $url; mplayer $(ls ${url##*=}*| tail -n1) -ss 00:57 -endpos 10 -vo gif89a:fps=5:output=output.gif -vf scale=400:300 -nosound


### Media.Video

	Extract audio from Flash video (*.flv) as mp3 file
	$ ffmpeg -i video.flv -vn -ar 44100 -ac 2 -ab 192k -f mp3 audio.mp3

	Cut out a piece of film from a file. Choose an arbitrary length and starting time.
	$ ffmpeg -vcodec copy -acodec copy -i orginalfile -ss 00:01:30 -t 0:0:20 newfile

	Play high-res video files on a slow processor
	$ mplayer -framedrop -vfm ffmpeg -lavdopts lowres=1:fast:skiploopfilter=all

	For all flv files in a dir, grab the first frame and make a jpg.
	$ for f in *.flv; do ffmpeg -y -i "$f" -f image2 -ss 10 -vframes 1 -an "${f%.flv}.jpg"; done

	Play music from youtube without download
	$ wget -q -O - `youtube-dl -b -g $url`| ffmpeg -i - -f mp3 -vn -acodec libmp3lame -| mpg123 -

	Extract audio from a video
	$ ffmpeg -i video.avi -f mp3 audio.mp3
	
	Using mplayer to play the audio only but suppress the video
	$ mplayer -novideo something.mpg




## Ubuntu 

	Send pop-up notifications on Gnome
	$ notify-send ["<title>"] "<body>"

	(Debian/Ubuntu) Discover what package a file belongs to
	$ dpkg -S /usr/bin/ls

	Fix Ubuntu's Broken Sound Server
	$ sudo killall -9 pulseaudio; pulseaudio >/dev/null 2>&1 &

