import { Component } from '@angular/core';
import { FormGroup} from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  // define list of items
  items= [];
  complted_items= [];
  todoItem: string = '';
  days: any;
  hours: any;
  minutes: any;
  seconds: any;
  timer_msg: any;
  timer_display_on = false;

  // Write code to push new item
  submitNewItem(event) {
    this.items.push(event.taskName);
  }

  // Write code to complete item
  completeItem(item: string) {
    let index: number = this.items.indexOf(item);
    if (index !== -1) {
      this.items.splice(index, 1);
      this.complted_items.push(item);
    }
  }

  // Write code to delete item
  deleteItem(item:string) {
    let index: number = this.items.indexOf(item);
    if (index !== -1) {
      this.items.splice(index, 1);
    } else {
      index = this.complted_items.indexOf(item);
      if (index !== -1) {
        this.complted_items.splice(index, 1);
      }
    }
  }

  showTimer() {
    this.timer_display_on = !this.timer_display_on;
    const countDownDate = new Date("December 1, 2020 00:00:00").getTime();
    setInterval(( )=> {
      const now = new Date().getTime();
      const distance = countDownDate - now;
      const days = Math.floor(distance / (1000 * 60 * 60 * 24));
      const hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
      const minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
      const seconds = Math.floor((distance % (1000 * 60)) / 1000);    
      this.timer_msg = {
        days,
        hours,
        minutes,
        seconds
      }
    }
  , 1000);
  }
}