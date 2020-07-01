# copying the colorscheme for Micro text editor
mkdir -p ~/.config/micro/colorschemes
cp bubblegum_mn.micro ~/.config/micro/colorschemes
cp settings.json ~/.config/micro
# An alias for Micro
echo 'alias xmicro="export TERM=xterm-256color;micro $1"' >> ~/.bashrc
source ~/.bashrc
