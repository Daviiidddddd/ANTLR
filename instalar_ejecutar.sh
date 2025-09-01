#!/bin/bash
set -e
echo "Script de preparación y ejecución (Linux) - ejecuta en la VM"

# 1) descargar ANTLR jar si no existe
if [ ! -f /usr/local/lib/antlr-4.13.1-complete.jar ]; then
    sudo mkdir -p /usr/local/lib
    echo "Descargando antlr-4.13.1-complete.jar a /usr/local/lib ..."
    sudo wget -O /usr/local/lib/antlr-4.13.1-complete.jar https://www.antlr.org/download/antlr-4.13.1-complete.jar
else
    echo "ANTLR jar ya existe en /usr/local/lib"
fi

echo "Agregar alias temporales (puedes añadirlos a ~/.bashrc):"
echo "alias antlr4='java -jar /usr/local/lib/antlr-4.13.1-complete.jar'"
echo "alias grun='java org.antlr.v4.gui.TestRig'"

# 2) generar fuentes Java y Python
echo "Generando fuentes ANTLR (Java + visitor)"
java -jar /usr/local/lib/antlr-4.13.1-complete.jar -visitor LabeledExpr.g4

echo "Generando fuentes ANTLR (Python + visitor)"
java -jar /usr/local/lib/antlr-4.13.1-complete.jar -Dlanguage=Python3 -visitor LabeledExpr.g4

# 3) compilar Java
echo "Compilando Java..."
javac *.java

# 4) instalar runtime python
if ! python3 -c "import antlr4" &>/dev/null; then
    echo "Instalando antlr4 runtime para Python..."
    pip3 install --user antlr4-python3-runtime
else
    echo "antlr4 runtime para Python ya instalado"
fi

echo "Listo. Ejecuta:"
echo "  java Calc input.txt    # calculadora Java"
echo "  python3 calc_py.py input.txt   # calculadora Python"
