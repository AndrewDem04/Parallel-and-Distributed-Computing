import subprocess
import re
# I created this program for more accurate results 
# I used python because i has a lot of helpfull libraries 
def run_java_program(num_steps, num_threads, runs=5):
    times = []
    for _ in range(runs):
        # You can change eazily the file 
        result = subprocess.run(
    ['java', '-cp', '.', 'ParallelPiIntergration', str(num_steps), str(num_threads)],
    capture_output=True,
    text=True,
    check=True,
    # I had prolems with finding the program
    cwd="C:\\Users\\andre\\Downloads\\Lab6-20250417T154648Z-001\\Lab6\\HomeWork6-ics23169"
    )



        output = result.stdout
        match = re.search(r'time to compute = (\d+\.\d+) seconds', output)
        if match:
            times.append(float(match.group(1)))
    
    return sum(times) / len(times) if times else 0

def main():
    step_counts = [1_000_000, 10_000_000, 100_000_000,1_000_000_000]
    thread_counts = [1, 2, 4, 8]
    
    for steps in step_counts:
        print(f"\nSteps: {steps}")
        print("Threads | Average Time (s)")
        print("--------|-------------------")
        
        for threads in thread_counts:
            avg_time = run_java_program(steps, threads)
            print(f"{threads:6}  | {avg_time:.4f}")

if __name__ == "__main__":
    main()
