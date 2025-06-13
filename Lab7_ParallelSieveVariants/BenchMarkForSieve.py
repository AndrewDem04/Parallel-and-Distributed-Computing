import subprocess
import time
import statistics

# Java class names to test
classes = ["SieveStatic", "SieveCyclic", "SieveDynamic"]
# Input sizes to test
sizes = [10000000, 100000000 ,200000000]
# Thread counts
thread_counts = [2, 4, 8]
# Number of times to repeat each test
repeats = 3

def run_java_program(class_name, size, threads):
    try:
        result = subprocess.run(
            ["java", class_name, str(size), str(threads)],
            capture_output=True, text=True)
        output = result.stdout
        for line in output.splitlines():
            if "time in ms" in line:
                return float(line.strip().split("=")[-1])
    except Exception as e:
        print(f"Error running {class_name}: {e}")
    return None

def main():
    for class_name in classes:
        print(f"\n=== {class_name} ===")
        for size in sizes:
            print(f"\n{class_name} - Size: {size}")
            print(f"{'Threads':<8} | {'Avg Time (ms)'}")
            print("-" * 25)
            for threads in thread_counts:
                times = []
                for _ in range(repeats):
                    t = run_java_program(class_name, size, threads)
                    if t is not None:
                        times.append(t)
                if times:
                    avg_time = statistics.mean(times)
                    print(f"{threads:<8} | {avg_time:.4f}")
                else:
                    print(f"{threads:<8} | Failed")
                    
if __name__ == "__main__":
    main()
