# Calculadora ANTLR (Java y Python)

Repositorio con una calculadora creada a partir de la gramática `LabeledExpr.g4` (Capítulo 4 — *The Definitive ANTLR 4 Reference*).
Este trabajo fue realizado en una máquina virtual. El lenguaje del repo es **español** y las variables están en español para evitar nombres innecesarios en inglés.

## Qué incluye
- `LabeledExpr.g4` : gramática extendida (funciones, unidad deg/rad, factorial `!`).
- Java:
  - `EvalVisitor.java` : Visitor que evalúa expresiones (con nombres de variables en español).
  - `Calc.java` : driver para ejecutar la calculadora en Java.
- Python:
  - `eval_visitor.py` : Visitor en Python (nombres en español).
  - `calc_py.py` : driver para ejecutar la calculadora en Python.
- `input.txt` : ejemplos de prueba.
- `.gitignore`
- `instalar_ejecutar.sh` : script con comandos para preparar entorno y ejecutar (Linux).
- `LICENSE` : MIT

## Cómo usar (resumen rápido)
Se asume Linux (o máquina virtual con Linux). Estos son los pasos (detallados están en el script `instalar_ejecutar.sh`):

1. Descargar `antlr-4.13.1-complete.jar` y colocarlo en `/usr/local/lib` o en una carpeta a elección.
2. Crear alias (o usar directamente `java -jar /ruta/al/antlr-4.13.1-complete.jar`):
   - `antlr4='java -jar /usr/local/lib/antlr-4.13.1-complete.jar'`
   - `grun='java org.antlr.v4.gui.TestRig'`
3. Generar fuentes:
   - Java: `antlr4 -visitor LabeledExpr.g4`
   - Python: `antlr4 -Dlanguage=Python3 -visitor LabeledExpr.g4`
4. Compilar Java:
   - `javac *.java`
5. Ejecutar:
   - Java: `java Calc input.txt`
   - Python: `python3 calc_py.py input.txt`  (asegúrate de `pip install antlr4-python3-runtime`)

## Notas importantes
- Factorial `!` está definido para enteros no-negativos. Si pasas un real, se toma el entero por floor.
- `mode deg` o `mode rad` cambia el modo global. Además puedes usar `sin(30 deg)` para forzar unidad por llamada.
- Se hicieron validaciones básicas (dominio de `sqrt`, `ln`, `log`).
- El trabajo se hizo en una **máquina virtual** (VM). Si usas una VM, revisa que tenga acceso a Internet para descargar el `.jar` y que tenga instalado `java`, `python3` y `pip`.

Si quieres, puedo automáticamente crear el repositorio en tu cuenta de GitHub (necesitarás proporcionar un token) o puedo darte los comandos exactos para crear el repositorio local y subirlo desde tu máquina virtual.

¡Disfruta y pruébala!