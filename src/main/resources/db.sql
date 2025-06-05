CREATE TABLE film (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    genero ENUM('ACCION', 'COMEDIA', 'DRAMA', 'OTRO'),
    director VARCHAR(255),
    fecha_estreno DATE,
    duracion INT,
    puntuacion DOUBLE,
    comentarios TEXT
);


INSERT INTO film (titulo, genero, director, fecha_estreno, duracion, puntuacion, comentarios) VALUES
('Mad Max: Fury Road', 'ACCION', 'George Miller', '2015-05-15', 120, 8.1, 'Acción intensa y visualmente impactante.'),
('The Hangover', 'COMEDIA', 'Todd Phillips', '2009-06-05', 100, 7.7, 'Comedia divertida sobre una despedida de soltero.'),
('The Shawshank Redemption', 'DRAMA', 'Frank Darabont', '1994-09-23', 142, 9.3, 'Drama carcelario profundamente humano.'),
('Unknown Indie Film', 'OTRO', 'Jane Doe', '2020-11-11', 90, 6.5, 'Película experimental de bajo presupuesto.');
