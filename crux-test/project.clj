(defproject juxt/crux-test "crux-git-version"
  :description "Crux Tests Project"
  :url "https://github.com/juxt/crux"
  :license {:name "The MIT License"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [juxt/crux-core "crux-git-version-beta"]

                 ;; TPCH Fixture
                 [io.airlift.tpch/tpch "0.10"]

                 ;; General:
                 [org.clojure/test.check "0.10.0"]]

  :jvm-opts ["-server" "-Xmx8g" "-Dlogback.configurationFile=test-resources/logback-test.xml"]
  :middleware [leiningen.project-version/middleware]
  :global-vars {*warn-on-reflection* true}
  :pedantic? :warn

  :profiles {:dev {:dependencies [[circleci/circleci.test "0.4.3"]]}
             :test {:dependencies [[juxt/crux-rocksdb "crux-git-version-beta"]
                                   [juxt/crux-lmdb "crux-git-version-alpha"]
                                   [juxt/crux-kafka "crux-git-version-beta"]
                                   [juxt/crux-kafka-connect "crux-git-version-beta"]
                                   [juxt/crux-kafka-embedded "crux-git-version-beta"]
                                   [juxt/crux-jdbc "crux-git-version-beta"]
                                   [juxt/crux-http-client "crux-git-version-beta"]
                                   [juxt/crux-http-server "crux-git-version-alpha"]
                                   [juxt/crux-metrics "crux-git-version-alpha"]
                                   [juxt/crux-rdf "crux-git-version-alpha"]

                                   ;; JDBC
                                   [com.h2database/h2 "1.4.199"]
                                   [com.opentable.components/otj-pg-embedded "0.13.1"]
                                   [org.xerial/sqlite-jdbc "3.28.0"]
                                   [mysql/mysql-connector-java "8.0.17"]
                                   [com.microsoft.sqlserver/mssql-jdbc "8.2.2.jre8"]

                                   ;; Uncomment to test Oracle, you'll need to locally install the JAR:
                                   ;; [com.oracle/ojdbc "12.2.0.1"]

                                   ;; General:
                                   [ch.qos.logback/logback-classic "1.2.3"]
                                   [org.clojure/data.json "0.2.7"]
                                   [org.clojure/data.csv "1.0.0"]
                                   [clj-http "3.10.1"]

                                   ;; Outer tests:
                                   [org.eclipse.rdf4j/rdf4j-repository-sparql "3.0.0"]
                                   [criterium "0.4.5"]

                                   ;; Authenticated HTTP Server/Client Tests
                                   [com.nimbusds/nimbus-jose-jwt "8.2.1" :exclusions [net.minidev/json-smart]]
                                   [net.minidev/json-smart "2.3"]

                                   ;; dependency conflict resolution
                                   [commons-codec "1.12"]
                                   [com.fasterxml.jackson.core/jackson-core "2.10.2"]
                                   [com.fasterxml.jackson.core/jackson-annotations "2.10.2"]
                                   [com.fasterxml.jackson.core/jackson-databind "2.10.2"]
                                   [org.tukaani/xz "1.8"]
                                   [com.github.spotbugs/spotbugs-annotations "3.1.9"]
                                   [org.eclipse.jetty/jetty-util "9.4.22.v20191022"]
                                   [org.eclipse.jetty/jetty-http "9.4.22.v20191022"]]}}

  :aliases {"test" ["with-profile" "+test" "run" "-m" "circleci.test/dir" :project/test-paths]
            "tests" ["with-profile" "+test" "run" "-m" "circleci.test"]
            "retest" ["with-profile" "+test" "run" "-m" "circleci.test.retest"]})
