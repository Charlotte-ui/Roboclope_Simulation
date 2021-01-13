import os
import time
import random
from stat import *

fichier=1

while True:
    time.sleep(random.randint(0,2))
    i = random.randint(0,3)
    if i == 0:
        nomFichier="python/" + str(fichier) + ".txt"
        f = open(nomFichier, "w")
        x= random.random()*10
        y= random.random()*10
        d= random.random()*10
        f.write(str(x)+"\n")
        f.write(str(y)+"\n")
        f.write(str(d)+"\n")
        f.close()
        fichier=fichier+1
    
