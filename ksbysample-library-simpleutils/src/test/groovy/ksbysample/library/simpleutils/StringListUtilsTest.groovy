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

    @Unroll
    def "sumStringLength(#stringList) --> #result"() {
        expect:
        StringListUtils.sumStringLength(stringList) == result

        where:
        stringList            || result
        null                 || 0
        [null]               || 0
        ["a"]                || 1
        ["テスト"]           || 3
        ["a", "ab", "abc"]  || 6
        ["a", null, "abc"]  || 4
    }

    @Unroll
    def "maxLengthString(#stringList) --> #result"() {
        expect:
        StringListUtils.maxLengthString(stringList) == result

        where:
        stringList            || result
        null                 || Optional.empty()
        [null]               || Optional.empty()
        ["a"]                || Optional.of("a")
        ["テスト"]           || Optional.of("テスト")
        ["a", "ab", "abc"]  || Optional.of("abc")
        ["a", null, "abc"]  || Optional.of("abc")
    }

}
