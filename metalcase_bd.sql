-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 10-06-2024 a las 03:31:43
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `metalcase_bd`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `id` int(11) NOT NULL,
  `nombre` text NOT NULL,
  `telefono` bigint(20) DEFAULT NULL,
  `email` text NOT NULL,
  `direccion` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`id`, `nombre`, `telefono`, `email`, `direccion`) VALUES
(1, 'Juan Perez', 3413820859, 'juan.perez@example.com', 'Av. Siempre Viva 123'),
(2, 'Ana Gomez', 7854698546, 'ana.gomez@example.com', 'Calle Falsa 456'),
(4, 'Maria Lopez', 7654321098, 'maria.lopez@example.com', 'Insurgentes Sur 1011'),
(5, 'Carlos Martinez', 3413820858, 'carlos.martinez@example.com', 'Eje Central 1213'),
(7, 'Amelia Cammarata', 3464632600, 'cammarataamelia@gmail.com', 'Lisandro de la Torre 2611');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `produccion`
--

CREATE TABLE `produccion` (
  `id` int(11) NOT NULL,
  `nombreProduccion` text NOT NULL,
  `productoTerminado` int(11) NOT NULL,
  `materiaPrima` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `produccion`
--

INSERT INTO `produccion` (`id`, `nombreProduccion`, `productoTerminado`, `materiaPrima`) VALUES
(1, 'Produccion A', 3, 1),
(2, 'Produccion B', 4, 2),
(3, 'Produccion C', 5, 1),
(4, 'Produccion D', 5, 4),
(5, 'Produccion E', 9, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `producto`
--

CREATE TABLE `producto` (
  `id` int(11) NOT NULL,
  `nombreProducto` text NOT NULL,
  `descripcion` text NOT NULL,
  `precio` int(11) NOT NULL,
  `stock` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `producto`
--

INSERT INTO `producto` (`id`, `nombreProducto`, `descripcion`, `precio`, `stock`) VALUES
(1, 'Metal', '20kg', 100, 50),
(2, 'Acero', '40kg', 200, -2),
(3, 'Plancha de metal', '40cm', 150, 20),
(4, 'Plancha de Acero', '50cm', 250, 40),
(5, 'Tornillo', '20 Unidades', 300, 25),
(9, 'Fondo de Heladeras', '30cm x 80cm', 1200, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `proveedor`
--

CREATE TABLE `proveedor` (
  `id` int(11) NOT NULL,
  `nombreProveedor` text NOT NULL,
  `telefono` bigint(11) NOT NULL,
  `nombreProducto` int(11) NOT NULL,
  `productoStock` int(11) NOT NULL,
  `precioProducto` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `proveedor`
--

INSERT INTO `proveedor` (`id`, `nombreProveedor`, `telefono`, `nombreProducto`, `productoStock`, `precioProducto`) VALUES
(1, 'Jorge Caseros', 2465821035, 5, 250, 25),
(2, 'Proveedor 2', 2147483647, 2, 80, 180),
(3, 'Proveedor 3', 2147483647, 3, 60, 130),
(4, 'Proveedor 4', 2147483647, 4, 70, 220),
(5, 'Proveedor 5', 2147483647, 5, 50, 270);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `produccion`
--
ALTER TABLE `produccion`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `producto`
--
ALTER TABLE `producto`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cliente`
--
ALTER TABLE `cliente`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `produccion`
--
ALTER TABLE `produccion`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `producto`
--
ALTER TABLE `producto`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT de la tabla `proveedor`
--
ALTER TABLE `proveedor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
