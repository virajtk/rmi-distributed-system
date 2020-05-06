import { Component, OnInit } from '@angular/core';
import { Sensor } from './sensor.model';
import { SensorDataService } from './sensor-data.service';



@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  
  
  sensors$: Sensor[];
  
  constructor(private sensorService: SensorDataService) {}

  ngOnInit() {

    setInterval(()=>{
      return this.sensorService.getSensorDetails()
        .subscribe(data => this.sensors$ = data);
      },10000);
  }

}
