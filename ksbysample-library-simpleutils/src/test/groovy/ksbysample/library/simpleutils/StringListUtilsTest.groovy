package ksbysample.library.simpleutils

import spock.lang.Specification
import spock.lang.Unroll

class StringListUtilsTest extends Specification {

    @Unroll
    def "ConvertStringList2LengthCsv(#stringList) --> #result"() {
        expect:
        StringListUtils.convertStringList2LengthCsv(stringList) == result

        where:
        stringList            || result
        null                 || null
        [null]               || "0"
        ["a"]                || "1"
        ["テスト"]           || "3"
        ["a", "ab", "abc"]  || "1,2,3"
        ["a", null, "abc"]  || "1,0,3"
    }

}
