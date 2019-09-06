pwd=$(pwd)

echo "CLI_TOOLKIT_ROOT=\"$pwd/bin\"" > config.sh
echo "export CLI_TOOLKIT_ROOT" >> config.sh

echo 'Configuration is completed'
