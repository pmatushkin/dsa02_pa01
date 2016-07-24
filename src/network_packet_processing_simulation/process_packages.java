import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class Request {
    public Request(int arrival_time, int process_time) {
        this.arrival_time = arrival_time;
        this.process_time = process_time;
    }

    public int arrival_time;
    public int process_time;
}

class Response {
    public Response(boolean dropped, int start_time) {
        this.dropped = dropped;
        this.start_time = start_time;
    }

    public boolean dropped;
    public int start_time;
}

class Buffer {
    public Buffer(int size) {
        this.size_ = size;
        this.finish_time_ = new ArrayList<Integer>();
    }

    private int maxFinishTime = 0;

    public Response Process(Request request) {
        // write your code here

        // this handles the wait if the next packet doesn't arrive immediately
        if (maxFinishTime < request.arrival_time) {
            maxFinishTime = request.arrival_time;
        }

        // remove all finish_times lesser or equal than request.arrival_time
        int i = 0;
        int countPacketsInBuffer = this.finish_time_.size();
        while (i < countPacketsInBuffer) {
            if (finish_time_.get(i) <= request.arrival_time) {
                finish_time_.remove(i);
                countPacketsInBuffer--;
            } else {
                i++;
            }
        }

        // count the number of packets in finish_time_: this is how many packages are currently in the buffer
        countPacketsInBuffer = this.finish_time_.size();

        if (countPacketsInBuffer < this.size_) {
            // get the time when the buffer becomes empty
            int bufferFinishTime = maxFinishTime;

            // add a new package to the buffer
            maxFinishTime = maxFinishTime + request.process_time;
            finish_time_.add(maxFinishTime);

            return new Response(false, bufferFinishTime);
        } else {
            return new Response(true, -1);
        }
    }

    private int size_;
    private ArrayList<Integer> finish_time_;
}

class process_packages {
    private static ArrayList<Request> ReadQueries(Scanner scanner) throws IOException {
        int requests_count = scanner.nextInt();
        ArrayList<Request> requests = new ArrayList<Request>();
        for (int i = 0; i < requests_count; ++i) {
            int arrival_time = scanner.nextInt();
            int process_time = scanner.nextInt();
            requests.add(new Request(arrival_time, process_time));
        }
        return requests;
    }

    private static ArrayList<Response> ProcessRequests(ArrayList<Request> requests, Buffer buffer) {
        ArrayList<Response> responses = new ArrayList<Response>();
        for (int i = 0; i < requests.size(); ++i) {
            responses.add(buffer.Process(requests.get(i)));
        }
        return responses;
    }

    private static void PrintResponses(ArrayList<Response> responses) {
        for (int i = 0; i < responses.size(); ++i) {
            Response response = responses.get(i);
            if (response.dropped) {
                System.out.println(-1);
            } else {
                System.out.println(response.start_time);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        int buffer_max_size = scanner.nextInt();
        Buffer buffer = new Buffer(buffer_max_size);

        ArrayList<Request> requests = ReadQueries(scanner);
        ArrayList<Response> responses = ProcessRequests(requests, buffer);
        PrintResponses(responses);
    }
}
