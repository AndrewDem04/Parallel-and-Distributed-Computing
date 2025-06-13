import subprocess
import statistics

classes = ["ConcurrentMapWordCount", "LocalMapReductionWordCount"]
input_file = "PutYourInputFileHere.txt"  # Replace with your actual input file
thread_counts = [2, 4, 8]
repeats = 3

def extract_time_from_output(output):
    """Extract time in ms from Java program output."""
    for line in output.splitlines():
        if "time in seconds " in line:
            return float(line.strip().split("=")[-1]) 
    return None

for java_class in classes:
    print(f"\n=== {java_class} ===\n")
    print(f"{'Threads':<8} | {'Avg Time (s)':<12}")
    print("-" * 25)
    
    for threads in thread_counts:
        times = []

        for _ in range(repeats):
            try:
                result = subprocess.run(
                    ["java", java_class, input_file, str(threads)],
                    capture_output=True,  
                    text=True  
                )

                elapsed = extract_time_from_output(result.stdout)

                if elapsed is None:
                    times.append(None)
                else:
                    times.append(elapsed)

            except Exception as e:
                print(f"Error running {java_class}: {e}")
                times.append(None)

        if all(t is None for t in times):
            print(f"{threads:<8} | Failed")
        else:
            valid_times = [t for t in times if t is not None]
            avg_time = statistics.mean(valid_times)
            print(f"{threads:<8} | {avg_time:.4f}")
