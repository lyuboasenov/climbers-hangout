﻿using System;
using System.Collections.Generic;
using System.Text;

namespace ClimbersHangout.Core.Models {
   public class User {
      public Guid Id { get; set; }
      public string Username { get; set; }
      public string Email { get; set; }
   }
}