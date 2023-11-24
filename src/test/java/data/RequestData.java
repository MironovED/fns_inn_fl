package data;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Data
@RequiredArgsConstructor
@Value
public class RequestData {
    private String validInn = "520205004556";
    private String Surname = "ЧАХЛОВ";
    private String name = "ПЕТР";
    private String numberDUL = "919928";


}
