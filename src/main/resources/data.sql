-- Resetea la tabla y el contador de IDs a 1
TRUNCATE TABLE estudios;

-- Se insertan los datos
INSERT INTO estudios (nombre, pais_origen, ano_fundacion, sitio_web) VALUES
    ('FromSoftware', 'Japón', 1986, 'https://www.fromsoftware.jp'),
    ('Naughty Dog', 'Estados Unidos', 1984, 'https://www.naughtydog.com'),
    ('Team Cherry', 'Australia', 2014, 'https://www.teamcherry.com.au'),
    ('Firaxis Games', 'Estados Unidos', 1996, 'https://firaxis.com'),
    ('Capcom', 'Japón', 1983, 'https://www.capcom.com');