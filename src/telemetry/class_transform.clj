(ns telemetry.class-transform
  "Manipulate classes via the ASM library"
  ;;(:import (org.objectweb.asm ClassReader ClassWriter ClassVisitor Opcodes)))
  )

(def transforms
  {})



;(defn transform
;  "Apply a transform to the raw bytes representing a class.
;  Returns a byte array which is the result of the tansform."
;  [^bytes b transform]
;  (let [cr (ClassReader. b)
;        cw (ClassWriter. cr (bit-or ClassWriter/COMPUTE_FRAMES ClassWriter/COMPUTE_MAXS))
;        cv (ClassVisitor. Opcodes/ASM4 cw)]))


