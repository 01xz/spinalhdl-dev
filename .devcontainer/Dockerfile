ARG IMAGE="ubuntu:20.04"

FROM ${IMAGE} as common

RUN apt-get update && apt-get upgrade -y && apt-get install -y --no-install-recommends \
    apt-utils apt-transport-https ca-certificates \
    && update-ca-certificates

FROM common as build

ARG HTTP_PROXY

RUN apt-get install -y --no-install-recommends \
    git make perl python3 autoconf clang flex bison ccache gperf \
    libgoogle-perftools-dev numactl perl-doc \
    libfl2 libfl-dev zlibc zlib1g zlib1g-dev

RUN git config --global http.proxy ${HTTP_PROXY} \
    && git config --global https.proxy ${HTTP_PROXY}

RUN git clone https://github.com/verilator/verilator \
    && cd verilator \
    && git checkout stable \
    && unset VERILATOR_ROOT \
    && autoconf \
    && ./configure --prefix="/usr/local/" \
    && make -j$(nproc) \
    && make install DESTDIR="$(pwd)/install-verilator" \
    && mv install-verilator/usr/local /tmp/verilator \
    && cd ..

RUN git clone https://github.com/steveicarus/iverilog --depth=1 --branch v11-branch \
    && cd iverilog \
    && autoconf \
    && ./configure \
    && make -j$(nproc) \
    && make install DESTDIR="$(pwd)/install-iverilog" \
    && mv install-iverilog/usr/local /tmp/iverilog \
    && cd ..

FROM common as spinalhdl

ENV DEBIAN_FRONTEND=noninteractive
RUN apt-get install -y --no-install-recommends \
    make perl python3 python-is-python3 clang libfl2 libfl-dev zlibc zlib1g zlib1g-dev \
    ccache libgoogle-perftools-dev numactl gnupg2 curl git openjdk-11-jdk scala locales \
    && sed -i -e 's/# en_US.UTF-8 UTF-8/en_US.UTF-8 UTF-8/' /etc/locale.gen \
    && locale-gen

COPY --from=build /tmp/verilator /usr/local/.
COPY --from=build /tmp/iverilog /usr/local/.

FROM spinalhdl as spinalhdl-dev

ARG USERNAME=vscode
ARG USER_UID=1000
ARG USER_GID=$USER_UID

RUN apt-get -y install sudo && groupadd --gid $USER_GID $USERNAME \
    && useradd --uid $USER_UID --gid $USER_GID -m $USERNAME -s /usr/bin/bash \
    && echo $USERNAME ALL=\(root\) NOPASSWD:ALL > /etc/sudoers.d/$USERNAME \
    && chmod 0440 /etc/sudoers.d/$USERNAME \
    && rm -rf /var/lib/apt/lists/*

USER $USERNAME

ENTRYPOINT [ "/usr/bin/bash" ]
