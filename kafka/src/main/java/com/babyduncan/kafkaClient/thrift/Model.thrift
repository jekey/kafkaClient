################################################
# author : guohaozhao                          #
# email  : guohaozhao116008@sohu-inc.com       #
# since  : 2013-7-11 12:00                     #
################################################

namespace java com.babyduncan.kafkaClient.thrift

struct Domainsync {
    1:  string domain
    2:  i32 type
    3:  string passport
    4:  i64 sid
}
