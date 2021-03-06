(ns crux.index-version-override-test
  (:require [clojure.test :as t]
            [crux.api :as crux]
            [crux.codec :as c]
            [crux.fixtures :as fix])
  (:import crux.api.IndexVersionOutOfSyncException))

(t/deftest test-index-version-override
  (fix/with-tmp-dir "db-dir" [db-dir]
    (let [index-version c/index-version
          inc-index-version (inc index-version)
          topo {:crux.node/topology '[crux.standalone/topology crux.kv.rocksdb/kv-store]
                :crux.kv/db-dir db-dir}]

      (doto (crux/start-node topo) .close)

      (with-redefs [c/index-version inc-index-version]
        (t/testing "standard IVOOSE"
          (t/is (thrown? IndexVersionOutOfSyncException
                         (doto (crux/start-node topo)
                           (.close)))))

        (t/testing "version numbers have to match exactly"
          (t/is (thrown? IndexVersionOutOfSyncException
                         (doto (crux/start-node (assoc topo :crux.kv-indexer/skip-index-version-bump [(dec index-version) inc-index-version]))
                           (.close))))


          (t/is (thrown? IndexVersionOutOfSyncException
                         (doto (crux/start-node (assoc topo :crux.kv-indexer/skip-index-version-bump [index-version (inc inc-index-version)]))
                           (.close)))))

        (t/testing "supplying skip flag"
          (with-open [node (crux/start-node (assoc topo :crux.kv-indexer/skip-index-version-bump [index-version inc-index-version]))]
            (t/is node)))

        (t/testing "only need to supply skip-index-version-bump once"
          (with-open [node (crux/start-node topo)]
            (t/is node)))))))
