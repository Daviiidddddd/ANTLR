# Calculadora con ANTLR (Java y Python)

## David Castellanos

Este repositorio contiene la calculadora que se hizo siguiendo el **capítulo 4** del libro *The Definitive ANTLR 4 Reference*.  
La preparación y las pruebas se hicieron en **Ubuntu** dentro de la **máquina virtual de virtualbox**. pero también estan en los archivos lo demás. Está la gramática, el visitor en Java, la versión en Python y los scripts para ejecutar.

---

## Contenido del repositosrio

- `LabeledExpr.g4` — gramática extendida (soporta `sin`, `cos`, `tan`, `sqrt`, `ln`, `log`, `!` factorial y unidades `deg`/`rad`).
- Java:
  - `EvalVisitor.java` — Visitor en Java que evalúa expresiones (uso nombres en español).
  - `Calc.java` — programa principal para ejecutar la calculadora en Java.
- Python:
  - `eval_visitor.py` — Visitor en Python (nombres en español).
  - `calc_py.py` — programa principal para ejecutar la calculadora en Python.
- `input.txt` — ejemplos de prueba.
- `instalar_ejecutar.sh` — script con los comandos que de Ubuntu.


---

## Proceso

1. Se intala Java (OpenJDK) en Ubuntu.
2. Descargué `antlr-4.13.1-complete.jar` y lo usé para generar el lexer/parser/visitor.
3. Implementamos el `EvalVisitor` en Java basado en el ejemplo del libro, pero adaptado a `double` para manejar funciones trigonométricas y logaritmos, y luego se agrega:
   - soporte para `sin`, `cos`, `tan`, `sqrt`, `ln`, `log` (base 10).
   - operador postfix factorial `!` (para enteros no-negativos).
   - manejo de grados y radianes (global con `mode deg`/`mode rad` y sobrescrito por llamada: `sin(30 deg)`).
4. Aca se hace otra vez la implementación en Python (generando el parser con `-Dlanguage=Python3 -visitor` y escribiendo `eval_visitor.py`).
5. SE prueban las expresiones con `input.txt` en la máquina virtual.

---

## Lo que se necesita para llevarlo a cabo

- Ubuntu (probado en una VM)
- OpenJDK (jdk 17 o similar)
- `java`, `javac`
- `python3`, `pip3`
- `antlr-4.13.1-complete.jar`
- `antlr4-python3-runtime` (para la versión Python)

---

## Pasos para ejecutar (lo que se ejecuto en la virtual box)

> Primero, se busca el `.jar` de ANTLR, descargarlo:
```bash
sudo mkdir -p /usr/local/lib
sudo wget -O /usr/local/lib/antlr-4.13.1-complete.jar https://www.antlr.org/download/antlr-4.13.1-complete.jar
```

> Generar fuentes desde la gramática:
```bash
# Generar Java (visitor)
java -jar /usr/local/lib/antlr-4.13.1-complete.jar -visitor LabeledExpr.g4

# Generar Python (visitor)
java -jar /usr/local/lib/antlr-4.13.1-complete.jar -Dlanguage=Python3 -visitor LabeledExpr.g4
```

> Compilar Java y preparar Python:
```bash
# Compilar Java
javac *.java

# Instalar runtime Python (si hace falta)
pip3 install --user antlr4-python3-runtime
```

> Ejecutar las pruebas que se usaron:
```bash
# Ejecutar calculadora en Java con el archivo de ejemplo
java Calc input.txt

# Ejecutar calculadora en Python
python3 calc_py.py input.txt
```

El script `instalar_ejecutar.sh` contiene exactamente los comandos que usé y puede ejecutarse en Ubuntu (dentro de la VM) para reproducir todo.

---

## Notas importantes (cosas a tener en cuenta)

- El factorial `!` está pensado para enteros no-negativos entonces si le pasamos un real, lo redondeamos hacia abajo (y se transforma a entero).
- `mode deg` / `mode rad` cambia el comportamiento trigonométrico global. También se puede usar `sin(30 deg)` para forzar grados solo en esa llamada.
- Validé dominios: `sqrt(x)` con `x >= 0`, `ln(x)` y `log(x)` con `x > 0`.
- Esto aplica principalmente para Ubuntui

---

## Archivos y su propósito

- `LabeledExpr.g4` — gramática (es la base para generar el parser).
- `EvalVisitor.java` — evaluación en Java.
- `Calc.java` — ejecutable principal en Java.
- `eval_visitor.py` — evaluación en Python.
- `calc_py.py` — ejecutable principal en Python.
- `input.txt` — casos de prueba que usé.
- `instalar_ejecutar.sh` — script para automatizar la preparación en Ubuntu.


---

## Tarea de ANTLR 1 de septiembre


