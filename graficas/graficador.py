# Graficador soluciones aceptadas en el recocido simulado
import matplotlib.pyplot as plt

with open('Corrida.txt') as f: # Los costos deben estar en un archivo llamado Corrida.txt
    lines = f.readlines()
    y = [line.split()[1] for line in lines]

z = []
for numero in y[0::15]:
    z.append(numero)
    
plt.plot(z)
plt.ylabel('Costo')
plt.show()
