# Lab5_RGBtoGrayScaleParallel

This project demonstrates parallel image processing in Java by converting RGB images to grayscale using multiple threads. The program splits the image horizontally, assigning each section to a separate thread for improved performance.

## How It Works

- The input image is divided into vertical sections, each processed by a separate thread.
- Each thread converts its assigned section from RGB to grayscale using the formula:
    ```
    gray = 0.299 * R + 0.587 * G + 0.114 * B
    ```
- The processed image is saved as a new file.

## Usage

```sh
java Lab5_RGBtoGrayScaleParallel <input_file> <output_file> <number_of_threads>
```

- `<input_file>`: Path to the image to convert.
- `<output_file>`: Path to save the grayscale image.
- `<number_of_threads>`: Number of threads to use for processing.

## Example

```sh
java Lab5_RGBtoGrayScaleParallel input.jpg output.jpg 4
```

## Performance Results

| Image            | Size   | 1T   | 2T   | 4T   | 8T   |
|------------------|--------|------|------|------|------|
| 1house.jpg       | 1264KB | 253  | 174  | 157  | 145  |
| 2aerial.jpg      | 1728KB | 539  | 387  | 405  | 381  |
| 3tiger.jpg       | 2084KB | 1320 | 876  | 769  | 685  |
| 4food.jpg        | 2644KB | 1148 | 918  | 954  | 717  |
| 5landscape.jpg   | 2933KB | 847  | 632  | 710  | 461  |
| 6berries.jpg     | 3998KB | 1032 | 876  | 643  | 729  |
| 7lake.jpg        | 6252KB | 1375 | 1060 | 835  | 853  |

*Times are in milliseconds.*

### Notes

- Increasing the number of threads generally improves performance, especially for larger images.
- Speedup is not always linear due to overhead and image size.
- For small images, more threads may not yield better performance.

## Code Structure

- **RGBtoGrayScaleParallel**: Main class that handles argument parsing, image loading, thread management, and saving the output.
- **GrayScaleWorker**: Worker thread class that processes a section of the image.

## Requirements

- Java 8 or higher
