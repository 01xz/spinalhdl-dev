docker build -t 01xz/spinalhdl-dev:latest --build-arg HTTP_PROXY=192.168.6.109:20171 -f Dockerfile .
docker push 01xz/spinalhdl-dev:latest
