import subprocess
import statistics

classes = ["ParallelStreamWordCountBuffered", "ParallelStreamWordCountList"]
input_file = "PutYourInputFileHere.txt"  # Replace with your actual input file
repeats = 3

def extract_time_from_output(output):
    """Extract time in seconds from Java program output."""
    for line in output.splitlines():
        if "time in seconds =" in line:
            return float(line.strip().split("=")[-1]) 
    return None

# Loop through each Java class
for java_class in classes:
    print(f"\n=== {java_class} ===\n")
    print(f"{'Avg Time (s)':<12}")
    print("-" * 25)
    
    # List to store all times for the current class
    times = []
    
    # Run multiple times for average computation
    for _ in range(repeats):
        try:
            result = subprocess.run(
                ["java", java_class, input_file],
                capture_output=True,  
                text=True  
            )

            # Extract the time from the output stream
            elapsed = extract_time_from_output(result.stdout)

            if elapsed is not None:
                times.append(elapsed)

        except Exception as e:
            print(f"Error running {java_class}: {e}")

    # Calculate and print the average time
    if times:
        avg_time = statistics.mean(times)
        print(f"{avg_time:.4f}")
    else:
        print("Failed to capture time")
